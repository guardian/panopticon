import React, { Component } from "react";
import styles from "./App.module.css";
import Table from "../Table/Table";
import { getAllRecords, DriveFileList } from "../../services/getAllRecords";

interface IAppState {
  driveFileList: DriveFileList | null
}

class App extends Component<{}, IAppState> {
  constructor(props: {}) {
    super(props);
    this.state = {
      driveFileList: null
    }
  }

  componentDidMount() {
    getAllRecords().then(value => {
      this.setState((state: IAppState): IAppState => {
        return { driveFileList: value };
      });
    });
  }

  render() {
    return (
      <div className={styles.app}>
        <header className={styles.header}>
          <Table />
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
