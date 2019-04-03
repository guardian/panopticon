import React, { Component } from "react";
import styles from "./App.module.css";

class App extends Component {
  render() {
    return (
      <div className={styles.app}>
        <header className={styles.header}>
          <p>
            Edit <code>src/App.tsx</code> and save to reload.
          </p>
          <a
            className={styles.link}
            href="https://reactjs.org"
            target="_blank"
            rel="noopener noreferrer"
          >
            Learn React
          </a>
        </header>
      </div>
    );
  }
}

export default App;
