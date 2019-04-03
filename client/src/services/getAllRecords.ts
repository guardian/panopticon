import fetch from "node-fetch";

interface IDriveFile {
  id: string;
  title: string;
  output: string;
}

export type DriveFileList = Array<IDriveFile>;

const baseURL = "http://localhost:3000";

async function getAllRecords(): Promise<DriveFileList> {
  const response = await fetch(`${baseURL}/api/getAllRecords`, {
    method: "get",
    credentials: "same-origin"
  });
  return await response.json();
}

export { getAllRecords };
