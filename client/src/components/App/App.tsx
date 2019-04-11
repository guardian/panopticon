import React, { Component } from "react";
import styles from "./App.module.css";
import Table from "../Table/Table";
import Header from "../Header/Header";
import { getAllRecords } from "../../services/getAllRecords";
import { DriveFileList } from "../../types/DriveModel"
import TagFilter from "../TagFilter/TagFilter";

interface IAppState {
  driveFileList: DriveFileList | null
  selectedTag: string | null
}

class App extends Component<{}, IAppState> {
  constructor(props: {}) {
    super(props);
    this.state = {
      driveFileList: null,
      selectedTag: null,
    }
  }

  setSelectedTag = (tagName: string) => {
    this.setState((prevState: IAppState): IAppState => {
      return { ...prevState, selectedTag: tagName };
    });
  };

  componentDidMount() {
    getAllRecords().then(value => {
      this.setState((prevState: IAppState): IAppState => {
        return { ...prevState, driveFileList: value };
      });
    });
  }

  render() {
    return (
      <div>
        <Header />
        <TagFilter setSelectedTag={this.setSelectedTag} />
        <Table records={this.state.driveFileList} />
      </div>
    );
  }
}

export default App;
