import React, { Component } from "react";
import styles from "./TagFilter.module.css";

interface ITagFilterProps {
}

interface ITagListProps {
  tagList: string[]
}

interface ITagFilterState {
}

const Header = () => (
  <h2 className={styles.header}>Select tags</h2>
)

const TagList = ({ tagList }: ITagListProps) => (
  <div>
    {tagList.map(tag => TagButton(tag))}
  </div>
)

const TagButton = (tagName: string) => (
  <button>{tagName}</button>
)

class TagFilter extends Component<ITagFilterProps, ITagFilterState> {
  constructor(props: ITagFilterProps) {
    super(props);
    this.state = {
    }
  }

  render() {
    return (
      <div>
        <Header />
        <TagList tagList={["tag1", "tag2"]} />
      </div>
    );
  }
}

export default TagFilter;
