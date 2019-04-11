import React, { Component } from "react";
import styles from "./TagFilter.module.css";

interface ITagFilterProps {
  selectedTag: string
}

interface ITagListProps {
  tagList: string[];
}

interface ITagButtonProps {
  selectTag: (tagName: string) => void;
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
  <button onClick={(event: React.MouseEvent<HTMLButtonElement>
  ) => {
    console.log(tagName);
  }}>{tagName}</button>
)



class TagFilter extends Component<ITagFilterProps, ITagFilterState> {
  constructor(props: ITagFilterProps) {
    super(props);
    this.state = {
    }
  }

  selectTag = (tagName: string) => {
    this.setState((state: ITagFilterState): ITagFilterState => {
      return { selectedTag: tagName };
    });
  };

  render() {
    return (
      <div>
        <Header />
        <TagList tagList={["Contributions", "GDPR"]} />
      </div>
    );
  }
}

export default TagFilter;
