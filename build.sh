#!/usr/bin/env bash
# Build the xr-composite tool. Downloads the pinned Filament desktop SDK (once, cached under
# build/sdk) and builds with CMake + Ninja. See README.md for the headless render recipe.
set -euo pipefail

# Keep in step with `filament-version` in .github/actions/build-xr-composite/action.yml —
# CI builds against that one, this is what a local ./build.sh uses. Renovate moves both
# together off the marker below; see renovate.json.
# renovate: datasource=github-releases depName=google/filament
FILAMENT_VERSION="${FILAMENT_VERSION:-v1.76.0}"
HERE="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
BUILD_DIR="${HERE}/build"
SDK_ROOT="${BUILD_DIR}/sdk/${FILAMENT_VERSION}"

case "$(uname -s)" in
  Linux)  PLATFORM="linux"  ;;
  Darwin) PLATFORM="mac"    ;;
  *) echo "unsupported platform: $(uname -s)" >&2; exit 1 ;;
esac

# Allow an externally provided SDK (CI cache, vendored copy).
if [[ -n "${FILAMENT_SDK:-}" && -f "${FILAMENT_SDK}/include/filament/Engine.h" ]]; then
  echo "Using pre-supplied FILAMENT_SDK=${FILAMENT_SDK}"
elif [[ -f "${SDK_ROOT}/filament/include/filament/Engine.h" ]]; then
  export FILAMENT_SDK="${SDK_ROOT}/filament"
  echo "Using cached SDK at ${FILAMENT_SDK}"
else
  echo "Downloading Filament ${FILAMENT_VERSION} (${PLATFORM}) ..."
  mkdir -p "${SDK_ROOT}"
  TGZ="filament-${FILAMENT_VERSION}-${PLATFORM}.tgz"
  URL="https://github.com/google/filament/releases/download/${FILAMENT_VERSION}/${TGZ}"
  curl -fL --retry 4 --retry-delay 2 -o "${SDK_ROOT}/${TGZ}" "${URL}"
  tar xzf "${SDK_ROOT}/${TGZ}" -C "${SDK_ROOT}"
  export FILAMENT_SDK="${SDK_ROOT}/filament"
fi

CC="${CC:-clang}"
CXX="${CXX:-clang++}"
cmake -S "${HERE}" -B "${BUILD_DIR}" -G Ninja \
  -DCMAKE_BUILD_TYPE=Release \
  -DCMAKE_C_COMPILER="${CC}" -DCMAKE_CXX_COMPILER="${CXX}" \
  -DFILAMENT_SDK="${FILAMENT_SDK}"
cmake --build "${BUILD_DIR}"

echo
echo "Built: ${BUILD_DIR}/xr-composite"
echo "Materials: ${BUILD_DIR}/materials/"
