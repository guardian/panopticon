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
  customProperties: {
    okr: string;
    team: string;
    quarter: string;
    year: string;
  };
}

const Row = ({ title, output, customProperties }: IRowProps) => (
  <tr>
    <td>{title}</td>
    <td>{output}</td>
    <td>{customProperties.okr}</td>
    <td>{customProperties.team}</td>
    <td>{customProperties.quarter}</td>
    <td>{customProperties.year}</td>
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
            <th>OKR</th>
            <th>Team</th>
            <th>Quarter</th>
            <th>Year</th>
          </tr>
        </thead>
        <tbody>
          {this.props.researchRecords ? this.props.researchRecords.map(row => (
            <Row key={row.title} title={row.title} output={row.output} customProperties={row.customProperties} />
          )) : null}
        </tbody>
      </table>
    );
  }
}

export default Table;
