import React, { Component, useState } from "react";
import styles from "./Table.module.css";
import { DriveFileList, IDriveFile } from "../../types/DriveModel";

interface ITableProps {
  records: DriveFileList;
}

interface ITableState {
  expandedRows: Array<String>;
}

interface ITableRowProps {
  id: string;
  title: string;
  output: string;
  okr: string;
  team: string;
  quarter: string;
  year: string;
  handleExpandRow: (id: string) => void;
}

const HeaderRow = (props: React.HTMLProps<HTMLDivElement>) => (
  <div {...props}>
    <div className={styles.cellTitle}>Title</div>
    <div className={styles.cell}>Output</div>
    <div className={styles.cell}>OKR</div>
    <div className={styles.cell}>Team</div>
    <div className={styles.cell}>Quarter</div>
    <div className={styles.cell}>Year</div>
  </div>
);

const Row = ({
  id,
  title,
  output,
  okr,
  team,
  quarter,
  year,
  handleExpandRow,
  ...props
}: React.HTMLProps<HTMLDivElement> & ITableRowProps) => (
  <div {...props} onClick={e => handleExpandRow(id)}>
    <div className={styles.cellTitle}>{title}</div>
    <div className={styles.cell}>{output}</div>
    <div className={styles.cell}>{okr}</div>
    <div className={styles.cell}>{team}</div>
    <div className={styles.cell}>{quarter}</div>
    <div className={styles.cell}>{year}</div>
  </div>
);

const RowExpanded = ({
  id,
  ...props
}: React.HTMLProps<HTMLDivElement> & { id: string }) => <div {...props} />;

const Table = ({ records }: ITableProps) => {
  const [expanded, setExpanded] = useState([]);

  const expandRowHandler = (id: string) => {
    expanded.includes(id) ? setExpanded([]) : setExpanded([id]);
  };

  return (
    <div className={styles.container}>
      <HeaderRow className={styles.header} />

      <div className={styles.containerScroll}>
        {records &&
          records.map(record => (
            <div key={record.id} className={styles.containerExpandedRow}>
              <Row
                className={styles.row}
                handleExpandRow={expandRowHandler}
                id={record.id}
                title={record.title}
                output={record.output}
                okr={record.okr}
                team={record.team}
                year={record.year}
                quarter={record.quarter}
              />
              {expanded.includes(record.id) && (
                <RowExpanded className={styles.rowExpanded} id={record.id} />
              )}
            </div>
          ))}
      </div>
    </div>
  );
};

export default Table;
