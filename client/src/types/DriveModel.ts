export interface IDriveFile {
    id: string;
    title: string;
    output: string;
    customProperties: {
        okr: string;
        team: string;
        quarter: string;
        year: string;
    };
}

export type DriveFileList = Array<IDriveFile>;