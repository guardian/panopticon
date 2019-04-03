import React, { Component } from "react";
import styles from "./Table.module.css";

interface ITableProps { }
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

class Table extends Component<ITableProps, ITableState> {
  constructor(props: ITableProps) {
    super(props);
    this.state = {
      rowData: [
        { title: "title1", output: "output1" },
        { title: "title2", output: "output2" },
        { title: "title3", output: "output3" }
      ]
    };
  }

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
          {this.state.rowData.map(row => (
            <Row key={row.title} title={row.title} output={row.output} />
          ))}
        </tbody>
      </table>
    );
  }
}

export default Table;
