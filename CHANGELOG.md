# Changelog

## 1.0.0 (2026-08-29)


### Features

* **cli:** auto-provision the xr-composite binary from releases ([#1732](https://github.com/yschimke/compose-preview-xr/issues/1732)) ([c72d991](https://github.com/yschimke/compose-preview-xr/commit/c72d991ad3a467ae8bf5b5427a516128fe7daeb5))
* **samples:** showcase spatial Compose previews + gradient composite backdrop ([#1741](https://github.com/yschimke/compose-preview-xr/issues/1741)) ([b02415d](https://github.com/yschimke/compose-preview-xr/commit/b02415d5143334d69059c84ed9fa245f6d9eea53))
* **xr-composite:** map the preview onto the device surface in the GLB preview ([#1996](https://github.com/yschimke/compose-preview-xr/issues/1996)) ([af3a76e](https://github.com/yschimke/compose-preview-xr/commit/af3a76eb785b3df3c15fb578310a75ba5b7021be))
* **xr-composite:** native Filament tool to bake spatial scenes to a composite PNG ([#1725](https://github.com/yschimke/compose-preview-xr/issues/1725)) ([8ac8bb1](https://github.com/yschimke/compose-preview-xr/commit/8ac8bb1c34d2013cb2977c029813698c63250431))
* **xr-composite:** rounded panels, soft shadow, edge rim + tighter framing ([#1745](https://github.com/yschimke/compose-preview-xr/issues/1745)) ([94aaffc](https://github.com/yschimke/compose-preview-xr/commit/94aaffcd6b618ef460f48a3b40192c947c97e80d))
* **xr:** make the render service handshake load-bearing ([#4781](https://github.com/yschimke/compose-preview-xr/issues/4781)) ([f7262c4](https://github.com/yschimke/compose-preview-xr/commit/f7262c486161a78dfbedc09e20fcd579c965fb0c))
* **xr:** multi-session support in the native render server ([#1802](https://github.com/yschimke/compose-preview-xr/issues/1802)) ([870781d](https://github.com/yschimke/compose-preview-xr/commit/870781d1f184fec09be34a018cb6acb08654fe11))
* **xr:** single-source SpatialScene codegen + a per-frame Filament render server ([#1779](https://github.com/yschimke/compose-preview-xr/issues/1779)) ([f0a669f](https://github.com/yschimke/compose-preview-xr/commit/f0a669f5e5a517005f69550298a5ebb55885f057))
* **xr:** single-source the XR render service protocol from a schema ([#4777](https://github.com/yschimke/compose-preview-xr/issues/4777)) ([f23dcef](https://github.com/yschimke/compose-preview-xr/commit/f23dcef8feda1bb363134eabdf4521a2ab136a59))
* **xr:** xr/structure data product (held panel tree + poses) ([#1806](https://github.com/yschimke/compose-preview-xr/issues/1806)) ([e434fa8](https://github.com/yschimke/compose-preview-xr/commit/e434fa8fbdb96b2fb64aab7e843cd00cdcda2443))


### Bug Fixes

* **xr-composite:** statically link libc++ so the Linux binary is self-contained ([#1737](https://github.com/yschimke/compose-preview-xr/issues/1737)) ([72581c8](https://github.com/yschimke/compose-preview-xr/commit/72581c8352f4ae8ea4eb095d8541e3e4bcc5cca3))
