import React, { Component } from "react";
import styles from "./Table.module.css";
import { DriveFileList } from "../../types/DriveModel";

interface ITableProps {
  researchRecords: DriveFileList
}

interface ITableState {
  rowData: Array<IRowProps>;
}
interface IRowProps {
  title: string;
  output: string;
}

const Row = ({ title, output }: IRowProps) => (
  <tr>
    <td>{title}</td>
    <td>{output}</td>
  </tr>
);

class Table extends Component<ITableProps, {}> {

  render() {
    return (
      <table>
        <thead>
          <tr>
            <th>Title</th>
            <th>Output</th>
          </tr>
        </thead>
        <tbody>
          {this.props.researchRecords ? this.props.researchRecords.map(row => (
            <Row key={row.title} title={row.title} output={row.output} />
          )) : null}
        </tbody>
      </table>
    );
  }
}

export default Table;
