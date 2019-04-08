import React, { Component } from "react";
import styles from "./Table.module.css";
import { DriveFileList } from "../../types/DriveModel";

interface ITableProps {
  researchRecords: DriveFileList
}

interface ITableState {
  expandedRows: Array<String>
}

interface IRowProps {
  id: string;
  title: string;
  output: string;
  okr: string;
  team: string;
  quarter: string;
  year: string;
  expandRow: (id:string) => void;
  className: string;
}

const Row = ({ id, title, output, okr, team, quarter, year, expandRow, className }: IRowProps) => (
  <div className={className} onClick={(event:React.MouseEvent<HTMLDivElement>) => {
    expandRow(id)
  }}>
    <div className={styles.cellTitle}>{title}</div>
    <div className={styles.cell}>{output}</div>
    <div className={styles.cell}>{okr}</div>
    <div className={styles.cell}>{team}</div>
    <div className={styles.cell}>{quarter}</div>
    <div className={styles.cell}>{year}</div>
  </div>
);

class Table extends Component<ITableProps, ITableState> {
  constructor(props: ITableProps) {
    super(props);
    this.state = {
      expandedRows: []
    }
  }

  expandRow = (id:string) => {
    this.setState((prevState) => {
        if(prevState.expandedRows.includes(id)) {
           return {expandedRows: []}
        } else {
           return {expandedRows: [id]}
      };
    });
  }

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
                key={row.id}
                id={row.id}
                title={row.title}
                output={row.output}
                okr={row.okr}
                team={row.team}
                quarter={row.quarter}
                year={row.year}
                expandRow={this.expandRow}
                className={this.state.expandedRows.includes(row.id) ? styles.newRow : styles.row}
              />
            ))
          : null}
      </div>
    );
  }
}

export default Table;
