import React, { useState } from "react";
import styles from "./Table.module.css";
import { DriveFileList } from "../../types/DriveModel";
import { SortDirection, TableColumn } from "../../types/AppModel";
import HeaderRow from "./HeaderRow/HeaderRow";

interface ITableProps {
  records: DriveFileList;
  handleColumnSort: (column: TableColumn, direction: SortDirection) => void;
}
interface ITableRowProps {
  id: string;
  title: string;
  output: string;
  outputIcon: string;
  outputUrl: string;
  okr: string;
  team: string;
  quarter: string;
  year: string;
  tags: string[];
  handleExpandRow: (id: string) => void;
}

const Row = ({
  id,
  title,
  output,
  outputIcon,
  outputUrl,
  okr,
  team,
  quarter,
  year,
  tags,
  handleExpandRow,
  ...props
}: React.HTMLProps<HTMLDivElement> & ITableRowProps) => (
    <div {...props} onClick={e => handleExpandRow(id)}>
      <div className={styles.cellTitle}>
        <a href={outputUrl} title="Preview Link in new tab" target="_blank" rel="noopener noreferrer">{title}</a>
      </div>
      <div className={styles.cell}>
        <img src={outputIcon} alt={`Google Drive ${output}`} />
      </div>
      <div className={styles.cell}>{okr}</div>
      <div className={styles.cell}>{team}</div>
      <div className={styles.cell}>{quarter}</div>
      <div className={styles.cell}>{year}</div>
      <div className={styles.cell}>
        {tags[0]}, {tags[1]}
      </div>
    </div>
  );

// TODO - add all the things inside
const RowExpanded = ({
  id,
  ...props
}: React.HTMLProps<HTMLDivElement> & { id: string }) => <div {...props} />;

const Table = ({ records, handleColumnSort }: ITableProps) => {
  const [expanded, setExpanded] = useState([]);

  const expandRowHandler = (id: string) => {
    expanded.includes(id) ? setExpanded([]) : setExpanded([id]);
  };

  return (
    <div className={styles.container}>
      <HeaderRow handleColumnSort={handleColumnSort} />

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
                outputIcon={record.outputIcon}
                outputUrl={record.outputUrl}
                okr={record.okr}
                team={record.team}
                year={record.year}
                quarter={record.quarter}
                tags={record.tags}
              />
              {/* {expanded.includes(record.id) && (
                <RowExpanded className={styles.rowExpanded} id={record.id} />
              )} */}
            </div>
          ))}
      </div>
    </div>
  );
};

export default Table;
