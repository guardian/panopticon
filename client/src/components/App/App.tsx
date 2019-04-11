import React, { Component } from "react";
import styles from "./App.module.css";
import Table from "../Table/Table";
import Header from "../Header/Header";
import TagFilter from "../TagFilter/TagFilter";
import { getAllRecords } from "../../services/getAllRecords";
import { DriveFileList } from "../../types/DriveModel";
import { TableColumn, SortDirection } from "../../types/AppModel";
import { compareValues } from "../../utils/sortingUtils";

interface IAppState {
  driveFileList: DriveFileList | null;
  tableFileList: DriveFileList | null;
  selectedTag: string | null;
}

class App extends Component<{}, IAppState> {
  constructor(props: {}) {
    super(props);
    this.state = {
      driveFileList: null,
      tableFileList: null,
      selectedTag: null
    };
  }

  clearTags = () => {
    const { driveFileList } = this.state
    this.setState(
      (prevState: IAppState): IAppState => {
        return { ...prevState, tableFileList: driveFileList }
      }
    )
  }

  setSelectedTag = (tagName: string) => {
    this.setState(
      (prevState: IAppState): IAppState => {
        return { ...prevState, selectedTag: tagName };
      },
      () => {
        this.filterFiles(this.state.selectedTag, this.state.driveFileList);
      }
    );
  };

  filterFiles = (selectedTag: string, driveFileList: DriveFileList) => {
    const filteredFiles = driveFileList.filter(driveFile =>
      driveFile.tags.includes(selectedTag)
    );

    this.setState(
      (prevState: IAppState): IAppState => {
        return { ...prevState, tableFileList: filteredFiles };
      }
    );
  };

  handleColumnSort = (column: TableColumn, direction: SortDirection) => {
    this.setState(
      (prevState: IAppState): IAppState => {
        return {
          ...prevState,
          tableFileList: [...prevState.tableFileList].sort(
            compareValues(column, direction)
          )
        };
      }
    );
  };

  componentDidMount() {
    getAllRecords().then(value => {
      this.setState(
        (prevState: IAppState): IAppState => {
          return { ...prevState, driveFileList: value, tableFileList: value };
        }
      );
    });
  }

  render() {
    return (
      <div>
        <Header />
        <TagFilter
          clearTags={this.clearTags}
          setSelectedTag={this.setSelectedTag} />
        <Table
          handleColumnSort={this.handleColumnSort}
          records={this.state.tableFileList}
        />
      </div>
    );
  }
}

export default App;
