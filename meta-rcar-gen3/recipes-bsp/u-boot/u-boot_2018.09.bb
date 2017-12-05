FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

require recipes-bsp/u-boot/u-boot.inc

DEPENDS += " bc-native dtc-native "

LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://Licenses/README;md5=a2c678cfd4a4d97135585cad908541c6"
PE = "1"

SRC_URI = " \
	git://git.denx.de/u-boot.git \
	file://0001-ARM-rmobile-Enable-RPC-on-Salvator-X-ULCB-Ebisu.patch \
	file://0002-ARM-rmobile-Enable-FIT-update-on-Gen3.patch \
	"

SRCREV = "4cdeda511f8037015b568396e6dcc3d8fb41e8c0"
S = "${WORKDIR}/git"

PV = "v2018.09+git${SRCPV}"

UBOOT_SREC ?= "u-boot-elf.srec"
UBOOT_SREC_IMAGE ?= "u-boot-elf-${MACHINE}-${PV}-${PR}.srec"
UBOOT_SREC_SYMLINK ?= "u-boot-elf-${MACHINE}.srec"

do_deploy_append() {
    install -m 644 ${B}/${UBOOT_SREC} ${DEPLOYDIR}/${UBOOT_SREC_IMAGE}
    cd ${DEPLOYDIR}
    rm -f ${UBOOT_SREC} ${UBOOT_SREC_SYMLINK}
    ln -sf ${UBOOT_SREC_IMAGE} ${UBOOT_SREC_SYMLINK}
    ln -sf ${UBOOT_SREC_IMAGE} ${UBOOT_SREC}
}
