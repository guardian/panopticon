import "node-fetch";
// import { DriveFileList } from "../types/driveAPI";

interface IDriveFile {
  id: string;
  title: string;
  output: string;
}

type DriveFileList = Array<IDriveFile>;

const baseURL = "http://localhost:9000";

async function getAllRecords(): Promise<DriveFileList> {
  return fetch(`${baseURL}/api/getAllRecords`, {
    method: "get",
    // headers: { 'Content-Type': 'application/json' },
    credentials: "same-origin"
  })
    .then(res => res.json())
    .then(json => json);
}

export { getAllRecords };
