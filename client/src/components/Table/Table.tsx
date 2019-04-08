import React, { Component } from "react";
import styles from "./Table.module.css";
import { DriveFileList } from "../../types/DriveModel";

interface ITableProps {
  researchRecords: DriveFileList
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
  <div className={styles.row}>
    <div className={styles.cell}>{title}</div>
    <div className={styles.cell}>{output}</div>
    <div className={styles.cell}>{customProperties.okr}</div>
    <div className={styles.cell}>{customProperties.team}</div>
    <div className={styles.cell}>{customProperties.quarter}</div>
    <div className={styles.cell}>{customProperties.year}</div>
  </div>
);

class Table extends Component<ITableProps, {}> {

  render() {
    return (
      <table className={styles.table}>
        <div>
          <div>
            <div className={styles.row}>Title</div>
            <div className={styles.row}>Output</div>
            <div className={styles.row}>OKR</div>
            <div className={styles.row}>Team</div>
            <div className={styles.row}>Quarter</div>
            <div className={styles.row}>Year</div>
          </div>
        </div>
        <div className={styles.row}>
          {this.props.researchRecords ? this.props.researchRecords.map(row => (
            <Row key={row.title} title={row.title} output={row.output} customProperties={row.customProperties} />
          )) : null}
        </div>
      </table>
    );
  }
}

export default Table;
