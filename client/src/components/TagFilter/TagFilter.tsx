import React, { Component } from "react";
import styles from "./TagFilter.module.css";

interface ITagFilterProps {
  setSelectedTag: (tagName: string) => void
}

const Header = () => (
  <h2 className={styles.header}>Select tags</h2>
)

const TagButton = (tagName: string, setSelectedTag: (tagName: string) => void) => (
  <button key={tagName} onClick={(event: React.MouseEvent<HTMLButtonElement>
  ) => {
    console.log(tagName);
    setSelectedTag(tagName)
  }}>{tagName}</button>
)



class TagFilter extends Component<ITagFilterProps, {}> {

  render() {
    const { setSelectedTag } = this.props
    return (
      <div>
        <Header />
        <div>
          {["Europe", "GDPR"].map(tag => TagButton(tag, setSelectedTag))}
        </div>
      </div>
    );
  }
}

export default TagFilter;
