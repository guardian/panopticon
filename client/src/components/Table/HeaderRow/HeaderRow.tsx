import React, { useState } from "react";
import styles from "./HeaderRow.module.css";
import { SortDirection, TableColumn } from "../../../types/AppModel";
import { toTitleCase } from "../../../utils/stringUtils";

interface ISortButtonProps {
  column: TableColumn;
  handleColumnSort: (column: TableColumn, direction: SortDirection) => void;
}

// TODO investigate typescript Array constant
const columns: Array<TableColumn> = [
  "Title",
  "Output",
  "OKR",
  "Team",
  "Quarter",
  "Year",
  "Tags"
];

const SortIcon = () => (
  <svg
    xmlns="http://www.w3.org/2000/svg"
    width="30"
    height="30"
    viewBox="0 0 24 24"
  >
    <path fill="none" d="M0 0h24v24H0V0z" />
    <path d="M16 17.01V10h-2v7.01h-3L15 21l4-3.99h-3zM9 3L5 6.99h3V14h2V6.99h3L9 3z" />
  </svg>
);

const SortButton = ({
  column,
  handleColumnSort,
  ...props
}: React.HTMLProps<HTMLButtonElement> & ISortButtonProps) => {
  const [direction, setDirection] = useState<SortDirection>("ASC");
  return (
    <button
      {...props}
      className={styles.buttonSort}
      onClick={e => {
        handleColumnSort(column, direction);
        setDirection(direction === "ASC" ? "DESC" : "ASC");
      }}
    >
      <SortIcon />
    </button>
  );
};

export default (
  props: React.HTMLProps<HTMLDivElement> & {
    handleColumnSort: (column: TableColumn, direction: SortDirection) => void;
  }
) => {
  const generateHeaderCell = (columnName: TableColumn) => (
    <div className={columnName === "Title" ? styles.cellTitle : styles.cell}>
      {toTitleCase(columnName)}
      <SortButton
        column={columnName}
        handleColumnSort={props.handleColumnSort}
      />
    </div>
  );

  return (
    <div className={styles.header} {...props}>
      {columns.map(columnName => generateHeaderCell(columnName))}
    </div>
  );
};
