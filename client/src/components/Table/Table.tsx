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
  <div className={styles.row} onClick={expandRow}>
    <div className={styles.cellTitle}>{title}</div>
    <div className={styles.cell}>{output}</div>
    <div className={styles.cell}>"test"</div>
    <div className={styles.cell}>"team"</div>
    <div className={styles.cell}>"quarter"</div>
    <div className={styles.cell}>"year"</div>
  </div>
);

const expandRow = (event) => {

}

class Table extends Component<ITableProps, {}> {

  render() {
    return (
      <div className={styles.table}>
        <div className={styles.row}>
          <div className={styles.cellTitle}>Title</div>
          <div className={styles.cell}>Output</div>
          <div className={styles.cell}>OKR</div>
          <div className={styles.cell}>Team</div>
          <div className={styles.cell}>Quarter</div>
          <div className={styles.cell}>Year</div>
        </div>
        {this.props.researchRecords
          ? this.props.researchRecords.map(row => (
              <Row
                key={row.title}
                title={row.title}
                output={row.output}
                customProperties={row.customProperties}
              />
            ))
          : null}
      </div>
    );
  }
}

export default Table;
