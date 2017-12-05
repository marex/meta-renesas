require recipes-bsp/u-boot/u-boot.inc

DEPENDS += " bc-native dtc-native "

LIC_FILES_CHKSUM = "file://Licenses/README;md5=a2c678cfd4a4d97135585cad908541c6"

SRC_URI = " \
	git://git.denx.de/u-boot.git \
	file://0001-Makefile-add-u-boot-elf.srec-if-CONFIG_REMAKE_ELF-is.patch \
	file://0002-Revert-arm-Make-gcc-6.0-or-later-a-hard-requirement-.patch \
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
