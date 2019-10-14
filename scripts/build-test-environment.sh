#!/bin/bash

WORKSPACE_DIR="${PWD}"

WORKING_DIR="/tmp/cinemanotifier"
DOWNLOAD_DIR="${WORKING_DIR}/downloads"
INSTALLER="${DOWNLOAD_DIR}/wildfly-18.0.0.Final.tar.gz"
TARGET_JBOSS_HOME="${WORKING_DIR}/wildfly-18.0.0.Final"

SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
SERVER_CONFIG_SRC="$(dirname ${SCRIPT_DIR})/.openshift/config/standalone-dev.xml"
SERVER_CONFIG_DST="${TARGET_JBOSS_HOME}/standalone/configuration/standalone.xml"


function install {
  if [[ ! -d "${DOWNLOAD_DIR}" ]]; then
    # CREATES THE WORKING_DIR AND DOWNLOAD_DIR
    mkdir -p "${DOWNLOAD_DIR}"
  fi
  if [[ ! -f ${INSTALLER} ]]; then
    cd "${DOWNLOAD_DIR}"
    echo "Downloading Wildfly installer to ${INSTALLER}"
    wget --quiet https://download.jboss.org/wildfly/18.0.0.Final/wildfly-18.0.0.Final.tar.gz
  fi
  cd "${WORKING_DIR}"
  tar xf "${INSTALLER}"
  cp "${SERVER_CONFIG_SRC}" -f "${SERVER_CONFIG_DST}"
}

if [[ -z "${JBOSS_HOME}" ]] && [[ ! -d "${TARGET_JBOSS_HOME}" ]]; then
  install
  echo "Wildfly installed to ${TARGET_JBOSS_HOME}"
fi

if [[ -z "${JBOSS_HOME}" ]]; then
  JBOSS_HOME="${TARGET_JBOSS_HOME}"
  echo "JBOSS_HOME set to ${JBOSS_HOME}"
fi

if [[ -z "${SENDGRID_API_KEY}" ]]; then
  echo "You need to have SENDGRID_API_KEY set in order to send emails"
  read -p "SENDGRID_API_KEY: " SENDGRID_API_KEY
fi

cd "${WORKSPACE_DIR}"
export JBOSS_HOME SENDGRID_API_KEY
unset WORKSPACE_DIR WORKING_DIR DOWNLOAD_DIR INSTALLER TARGET_JBOSS_HOME SERVER_CONFIG_SRC SERVER_CONFIG_DST install
