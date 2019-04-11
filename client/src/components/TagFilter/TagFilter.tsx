import React, { Component } from "react";
import styles from "./TagFilter.module.css";

interface ITagFilterProps {
  setSelectedTag: (tagName: string) => void
  clearTags: () => void
}

const Header = () => (
  <h2 className={styles.header}>Select tags</h2>
)

const TagButton = (tagName: string, setSelectedTag: (tagName: string) => void) => (
  <button key={tagName} onClick={(event: React.MouseEvent<HTMLButtonElement>
  ) => {
    setSelectedTag(tagName)
  }}>{tagName}</button>
)

class TagFilter extends Component<ITagFilterProps, {}> {

  render() {
    const { setSelectedTag, clearTags } = this.props
    const tags = ["Europe", "GDPR", "Contributions", "Accessibility", "Podcast", "Navigation", "Testtag", "Atoms", "Checkout", "Payment"]
    return (
      <div>
        <Header />
        <div>
          <button key="clear" onClick={(event: React.MouseEvent<HTMLButtonElement>) => {
            clearTags()
          }}>Clear all tags</button>
          <div>
            {tags.map(tag => TagButton(tag, setSelectedTag))}
          </div>
        </div>
      </div>
    );
  }
}

export default TagFilter;
