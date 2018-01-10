require recipes-bsp/u-boot/u-boot.inc

DEPENDS += " bc-native dtc-native "

LIC_FILES_CHKSUM = "file://Licenses/README;md5=a2c678cfd4a4d97135585cad908541c6"

SRC_URI = " \
	git://git.denx.de/u-boot.git \
	file://0001-Makefile-add-u-boot-elf.srec-if-CONFIG_REMAKE_ELF-is.patch \
	file://0002-Revert-arm-Make-gcc-6.0-or-later-a-hard-requirement-.patch \
	file://0003-clk-rmobile-Assure-SD-IF-clock-are-configured-correc.patch \
	file://0004-drivers-mmc-Change-buffer-type-in-ALLOC_CACHE_ALIGN_.patch \
	file://0005-drivers-mmc-Avoid-memory-leak-in-case-of-failure.patch \
	file://0006-mmc-sanitize-includes-for-DM-i2c.patch \
	file://0007-mmc-sdhci-don-t-clear-SDHCI_INT_STATUS-register-duri.patch \
	file://0008-mmc-sdhci-do-not-compare-pointer-to-0.patch \
	file://0009-mmc-dm-get-the-IO-line-and-main-voltage-regulators-f.patch \
	file://0010-mmc-split-mmc_startup.patch \
	file://0011-mmc-move-the-MMC-startup-for-version-above-v4.0-in-a.patch \
	file://0012-mmc-make-ext_csd-part-of-struct-mmc.patch \
	file://0013-mmc-add-a-function-to-read-and-test-the-ext-csd-mmc-.patch \
	file://0014-mmc-introduce-mmc-modes.patch \
	file://0015-mmc-Add-a-function-to-dump-the-mmc-capabilities.patch \
	file://0016-mmc-use-mmc-modes-to-select-the-correct-bus-speed.patch \
	file://0017-cmd-mmc-display-the-mode-name-and-current-bus-speed-.patch \
	file://0018-mmc-refactor-SD-startup-to-make-it-easier-to-support.patch \
	file://0019-mmc-refactor-MMC-startup-to-make-it-easier-to-suppor.patch \
	file://0020-mmc-make-mmc_set_ios-return-status.patch \
	file://0021-mmc-Enable-signal-voltage-to-be-selected-from-mmc-co.patch \
	file://0022-mmc-Add-a-new-callback-function-to-perform-the-74-cl.patch \
	file://0023-mmc-add-power-cyle-support-in-mmc-core.patch \
	file://0024-mmc-add-a-new-mmc-parameter-to-disable-mmc-clock.patch \
	file://0025-mmc-disable-the-mmc-clock-during-power-off.patch \
	file://0026-mmc-Add-a-execute_tuning-callback-to-the-mmc-operati.patch \
	file://0027-mmc-add-HS200-support-in-MMC-core.patch \
	file://0028-mmc-Add-support-for-UHS-modes.patch \
	file://0029-mmc-disable-UHS-modes-if-Vcc-cannot-be-switched-on-a.patch \
	file://0030-mmc-Change-mode-when-switching-to-a-boot-partition.patch \
	file://0031-mmc-Retry-some-MMC-cmds-on-failure.patch \
	file://0032-mmc-use-the-right-voltage-level-for-MMC-DDR-and-HS20.patch \
	file://0033-mmc-add-a-library-function-to-send-tuning-command.patch \
	file://0034-dm-mmc-Add-a-library-function-to-parse-generic-dt-bi.patch \
	file://0035-mmc-meson_gx_mmc-fix-the-complie-error.patch \
	file://0036-mmc-dump-card-and-host-capabilities-if-debug-is-enab.patch \
	file://0037-dm-mmc-update-mmc_of_parse.patch \
	file://0038-mmc-Fixed-a-problem-with-old-sd-or-mmc-that-do-not-s.patch \
	file://0039-mmc-all-hosts-support-1-bit-bus-width-and-legacy-tim.patch \
	file://0040-mmc-fix-for-old-MMCs-below-version-4.patch \
	file://0041-mmc-don-t-use-malloc_cache_aligned.patch \
	file://0042-mmc-convert-most-of-printf-to-pr_err-and-pr_warn.patch \
	file://0043-mmc-make-UHS-and-HS200-optional.patch \
	file://0044-mmc-make-optional-the-support-for-eMMC-hardware-part.patch \
	file://0045-configs-openrd-removed-support-for-eMMC-hardware-par.patch \
	file://0046-configs-omapl138_lcdk-decrease-the-loglevel-to-reduc.patch \
	file://0047-dm-mmc-sandbox-Update-SD-card-emulation.patch \
	file://0048-am335x_hs_evm-Trim-options-in-SPL-to-reduce-binary-s.patch \
	file://0049-mmc-uniphier-sd-Use-mmc_of_parse.patch \
	file://0050-mmc-uniphier-sd-Properly-handle-pin-voltage-configur.patch \
	file://0051-mmc-uniphier-sd-Add-Renesas-RCar-quirks.patch \
	file://0052-mmc-uniphier-sd-Handle-Renesas-div-by-1.patch \
	file://0053-mmc-uniphier-sd-Add-Renesas-SDR104-HS200-tuning-supp.patch \
	file://0054-mmc-uniphier-sd-Handle-DMA-completion-flag-differenc.patch \
	file://0055-mmc-uniphier-sd-Always-check-controller-version.patch \
	file://0056-ARM-rmobile-Enable-HS200-mode-on-RCar-Gen3.patch \
	"

SRCREV = "f3dd87e0b98999a78e500e8c6d2b063ebadf535a"

PV = "v2018.01+git${SRCPV}"

UBOOT_SREC ?= "u-boot-elf.srec"
UBOOT_SREC_IMAGE ?= "u-boot-elf-${MACHINE}-${PV}-${PR}.srec"
UBOOT_SREC_SYMLINK ?= "u-boot-elf-${MACHINE}.srec"

do_deploy_append() {
    install -m 644 ${S}/${UBOOT_SREC} ${DEPLOYDIR}/${UBOOT_SREC_IMAGE}
    cd ${DEPLOYDIR}
    rm -f ${UBOOT_SREC} ${UBOOT_SREC_SYMLINK}
    ln -sf ${UBOOT_SREC_IMAGE} ${UBOOT_SREC_SYMLINK}
    ln -sf ${UBOOT_SREC_IMAGE} ${UBOOT_SREC}
}
