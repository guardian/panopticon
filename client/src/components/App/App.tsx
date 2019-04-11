import React, { Component } from "react";
import styles from "./App.module.css";
import Table from "../Table/Table";
import Header from "../Header/Header";
import { getAllRecords } from "../../services/getAllRecords";
import { DriveFileList } from "../../types/DriveModel"
import TagFilter from "../TagFilter/TagFilter";

interface IAppState {
  driveFileList: DriveFileList | null
  tableFileList: DriveFileList | null
  selectedTag: string | null
}

class App extends Component<{}, IAppState> {
  constructor(props: {}) {
    super(props);
    this.state = {
      driveFileList: null,
      tableFileList: null,
      selectedTag: null,
    }
  }

  setSelectedTag = (tagName: string) => {
    this.setState((prevState: IAppState): IAppState => {
      return { ...prevState, selectedTag: tagName };
    }, () => {
      this.filterFiles(this.state.selectedTag, this.state.driveFileList)
    });
  };

  filterFiles = (selectedTag: string, driveFileList: DriveFileList) => {
    const filteredFiles = driveFileList.filter(driveFile => driveFile.tags.includes(selectedTag))

    this.setState((prevState: IAppState): IAppState => {
      return { ...prevState, tableFileList: filteredFiles }
    })

  }

  componentDidMount() {
    getAllRecords().then(value => {
      this.setState((prevState: IAppState): IAppState => {
        return { ...prevState, driveFileList: value, tableFileList: value };
      });
    });
  }

  render() {
    return (
      <div>
        <Header />
        <TagFilter setSelectedTag={this.setSelectedTag} />
        <Table records={this.state.tableFileList} />
      </div>
    );
  }
}

export default App;
