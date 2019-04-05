import React, { Component } from "react";
import styles from "./Table.module.css";
import { DriveFileList } from "../../types/DriveModel";

interface ITableProps {
  researchRecords: DriveFileList
}

interface IRowProps {
  button: string;
  title: string;
  output: string;
  outputPreview: string;
  outputDownload: string;
  outputThumbnail: string;
  exportLinks: {
    link: string
  };
  customProperties: {
    okr: string;
    team: string;
    quarter: string;
    year: string;
  };
}

const Row = ({ title, output, outputPreview, outputDownload, outputThumbnail, exportLinks, customProperties }: IRowProps) => (
  <tr>
    <td><button>+</button></td>
    <td>{title}</td>
    <td>{output}</td>
    <td>{outputPreview}</td>
    <td>{outputDownload}</td>
    <td>{outputThumbnail}</td>
    <td>{exportLinks.link}</td>
    <td>{customProperties.okr}</td>
    <td>{customProperties.team}</td>
    <td>{customProperties.quarter}</td>
    <td>{customProperties.year}</td>
  </tr>
);

class Table extends Component<ITableProps, {}> {

  render() {
    return (
      <div className={styles.table}>
        <table>
          <thead>
            <tr>
              <th>Expand</th>
              <th>Title</th>
              <th>Output</th>
              <th>Preview</th>
              <th>Download</th>
              <th>Thumbnail</th>
              <th>Export Links</th>
              <th>OKR</th>
              <th>Team</th>
              <th>Quarter</th>
              <th>Year</th>
            </tr>
          </thead>
          <tbody>
            {this.props.researchRecords ? this.props.researchRecords.map(row => (
              <Row key={row.id}
                button={row.button}
                title={row.title}
                output={row.output}
                outputPreview={row.outputPreview}
                outputDownload={row.outputDownload}
                outputThumbnail={row.outputThumbnail}
                exportLinks={row.exportLinks}
                customProperties={row.customProperties} />
            )) : null}
          </tbody>
        </table>
      </div>
    );
  }
}

export default Table;
