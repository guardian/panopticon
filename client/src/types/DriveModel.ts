export interface IDriveFile {
    id: string;
    title: string;
    output: string;
    okr: string;
    team: string;
    quarter: string;
    year: string;
}

export type DriveFileList = Array<IDriveFile>;
