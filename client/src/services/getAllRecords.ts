import fetch from "node-fetch";
// import { DriveFileList } from "../types/driveAPI";

interface IDriveFile {
  id: string;
  title: string;
  output: string;
}

type DriveFileList = Array<IDriveFile>;

const baseURL = "http://localhost:3000";

async function getAllRecords(): Promise<DriveFileList> {
  console.log("inside get all records");
  const response = fetch(`${baseURL}/api/getAllRecords`, {
    method: "get",
    credentials: "same-origin"
  });
  const jsonData = await response.json();
  return jsonData;
}

export { getAllRecords };
