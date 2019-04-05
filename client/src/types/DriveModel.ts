export interface IDriveFile {
    id: string;
    button: string;
    title: string;
    output: string;
    outputPreview: string;
    outputDownload: string;
    outputThumbnail: string;
    exportLinks: {
        link: string
    };
    customProperties: {
        okr: string;
        team: string;
        quarter: string;
        year: string;
    };
}

export type DriveFileList = Array<IDriveFile>;
