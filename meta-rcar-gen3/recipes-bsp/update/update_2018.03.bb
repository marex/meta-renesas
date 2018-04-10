SUMMARY = "Firmware update fitImage"
SECTION = "firmware"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

PACKAGE_ARCH = "${MACHINE_ARCH}"
COMPATIBLE_MACHINE = "(salvator-x|h3ulcb|m3ulcb|draak|eagle|ebisu)"

DEPENDS = " u-boot-mkimage-native dtc-native "
do_compile[depends] = " \
	virtual/bootloader:do_deploy \
	arm-trusted-firmware:do_deploy \
	${@bb.utils.contains_any('MACHINE', ['salvator-x','m3ulcb','h3ulcb','ebisu'], 'optee-os:do_deploy', '', d)} \
	"

SRC_URI = " \
	file://update.its \
	file://update-optee.its \
	"
S = "${WORKDIR}"

do_configure[noexec] = "1"
do_install[noexec] = "1"

do_compile() {
	cp ${DEPLOY_DIR_IMAGE}/bootparam_sa0-${MACHINE}.bin ${S}/
	cp ${DEPLOY_DIR_IMAGE}/bl2-${MACHINE}.bin ${S}/
	cp ${DEPLOY_DIR_IMAGE}/cert_header_sa6-${MACHINE}.bin ${S}/
	cp ${DEPLOY_DIR_IMAGE}/bl31-${MACHINE}.bin ${S}/
	cp ${DEPLOY_DIR_IMAGE}/u-boot-${MACHINE}.bin ${S}/

	if [ -e ${DEPLOY_DIR_IMAGE}/tee-${MACHINE}.bin ] ; then
		cp ${DEPLOY_DIR_IMAGE}/tee-${MACHINE}.bin ${S}/
		sed "s/#{MACHINE}/${MACHINE}/" update-optee.its > update-${MACHINE}.its
	else
		sed "s/#{MACHINE}/${MACHINE}/" update.its > update-${MACHINE}.its
	fi
	mkimage -f update-${MACHINE}.its update-${MACHINE}.itb
}

do_deploy() {
	install -d ${DEPLOY_DIR_IMAGE}
	install -m 0644 ${S}/update-${MACHINE}.itb ${DEPLOY_DIR_IMAGE}/update-${MACHINE}.itb
	ln -sf update-${MACHINE}.itb ${DEPLOY_DIR_IMAGE}/update.itb
}
addtask deploy before do_build after do_compile
