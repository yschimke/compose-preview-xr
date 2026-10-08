# Changelog

## [2.0.2](https://github.com/yschimke/compose-preview-xr/compare/v2.0.1...v2.0.2) (2026-10-08)


### Bug Fixes

* **deps:** plugin 2.35.0 and contracts 3.15.0; hold the daemon at 3.12.0 ([#41](https://github.com/yschimke/compose-preview-xr/issues/41)) ([86f0e7e](https://github.com/yschimke/compose-preview-xr/commit/86f0e7e721d2aabbd66f49c1e8e91a2c25d14f24))
* **deps:** plugin 2.35.1 and daemon 3.14.1; fix the xr-spatial pose tests on XR rc02 ([#42](https://github.com/yschimke/compose-preview-xr/issues/42)) ([799ca84](https://github.com/yschimke/compose-preview-xr/commit/799ca840c10caef8106ebc5949de030bbc8c21d7))
* **deps:** update androidx to v1.0.0-rc02 ([#33](https://github.com/yschimke/compose-preview-xr/issues/33)) ([fc6be41](https://github.com/yschimke/compose-preview-xr/commit/fc6be41ec2cb175126f96052019572f66484f084))
* **deps:** update compose-preview-daemon ([#35](https://github.com/yschimke/compose-preview-xr/issues/35)) ([bd95676](https://github.com/yschimke/compose-preview-xr/commit/bd9567661b1ff6fc2ab50e22a1b01e65318b11bb))
* **deps:** update roborazzi to v1.76.0 ([#36](https://github.com/yschimke/compose-preview-xr/issues/36)) ([bfb5c31](https://github.com/yschimke/compose-preview-xr/commit/bfb5c31fef3b5bf18be67c520cfed11c2453c069))

## [2.0.1](https://github.com/yschimke/compose-preview-xr/compare/v2.0.0...v2.0.1) (2026-10-02)


### Bug Fixes

* **deps:** pin compose-preview-daemon artifacts separately from the plugin ([#31](https://github.com/yschimke/compose-preview-xr/issues/31)) ([f4b524f](https://github.com/yschimke/compose-preview-xr/commit/f4b524f23cb7b5e75a95370b316245ae3f42c90d))
* **deps:** update androidx ([#22](https://github.com/yschimke/compose-preview-xr/issues/22)) ([5312fee](https://github.com/yschimke/compose-preview-xr/commit/5312feeaf1e14827854a914ce35113d5610536e8))
* **deps:** update compose-ai-tools to v1.85.0 ([#16](https://github.com/yschimke/compose-preview-xr/issues/16)) ([a2b486c](https://github.com/yschimke/compose-preview-xr/commit/a2b486c7c013dd3fc0c835aeb44186ea97acc227))
* **deps:** update dependency androidx.compose:compose-bom to v2026.09.00 ([#26](https://github.com/yschimke/compose-preview-xr/issues/26)) ([4fa4604](https://github.com/yschimke/compose-preview-xr/commit/4fa4604181bd745f0fe5713ce0ebf3bbda783959))
* **deps:** update dependency ee.schimke.composeai:data-render-core to v2.11.0 ([#17](https://github.com/yschimke/compose-preview-xr/issues/17)) ([d718780](https://github.com/yschimke/compose-preview-xr/commit/d71878075b3bcd196ae03381d1de695661480e0e))
* **deps:** update dependency ee.schimke.composeai:data-render-core to v2.20.0 ([#27](https://github.com/yschimke/compose-preview-xr/issues/27)) ([d29f20e](https://github.com/yschimke/compose-preview-xr/commit/d29f20e812dbc978d0d48e67a675354cda37436d))
* **deps:** update dependency org.robolectric:robolectric to v4.17 ([#23](https://github.com/yschimke/compose-preview-xr/issues/23)) ([e98a129](https://github.com/yschimke/compose-preview-xr/commit/e98a129804b12c18c74a51f8050280dadbd2cd06))
* **deps:** update roborazzi to v1.75.0 ([#29](https://github.com/yschimke/compose-preview-xr/issues/29)) ([ec213f5](https://github.com/yschimke/compose-preview-xr/commit/ec213f512c969e504379f315fc0d5af06537a60a))

## [2.0.0](https://github.com/yschimke/compose-preview-xr/compare/v1.0.1...v2.0.0) (2026-09-02)


### ⚠ BREAKING CHANGES

* **xr:** move renderer into XR repository ([#11](https://github.com/yschimke/compose-preview-xr/issues/11))

### Features

* **xr:** move renderer into XR repository ([#11](https://github.com/yschimke/compose-preview-xr/issues/11)) ([bb6614b](https://github.com/yschimke/compose-preview-xr/commit/bb6614bf3e707cac129f84d3080448ef00302796))

## [1.0.1](https://github.com/yschimke/compose-preview-xr/compare/v1.0.0...v1.0.1) (2026-08-29)


### Bug Fixes

* support Filament 1.76 cubemap uploads ([#9](https://github.com/yschimke/compose-preview-xr/issues/9)) ([f4317c5](https://github.com/yschimke/compose-preview-xr/commit/f4317c5ab888193402c6d8c9f8c740b223cc0f00))

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
