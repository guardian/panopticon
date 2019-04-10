import React, { Component } from "react";
import styles from "./Header.module.css";
import logo from '../../images/panopticonLogo.png';

class Header extends React.Component {

  render() {
    return (
      <div className={styles.header}>
        <img src={logo} alt="Logo" className={styles.headerLogo} />
      </div>
    )
  }
}

export default Header;
