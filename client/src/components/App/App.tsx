import React, { Component } from "react";
import styles from "./App.module.css";
import Table from "../Table/Table";
import { getAllRecords } from "../../services/getAllRecords";
import { DriveFileList } from "../../types/DriveModel"

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
          <Table researchRecords={this.state.driveFileList} />
        </header>
      </div>
    );
  }
}

export default App;
