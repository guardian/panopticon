import React, { Component } from "react";
import styles from "./App.module.css";
import Table from "../Table/Table";
import { getAllRecords } from "../../services/getAllRecords";
import { DriveFileList } from "../../types/DriveModel";

interface IAppState {
  driveFileList: DriveFileList | null;
}

class App extends Component<{}, IAppState> {
  constructor(props: {}) {
    super(props);
    this.state = {
      driveFileList: null
    };
  }

  componentDidMount() {
    getAllRecords().then(value => {
      this.setState(
        (state: IAppState): IAppState => {
          return { driveFileList: value };
        }
      );
    });
  }

  render() {
    return (
      <div>
        <Table records={this.state.driveFileList} />
      </div>
    );
  }
}

export default App;
