import React from 'react';
import { Button } from '@mui/material';

interface FileUploadButtonProps {
    onFileChange: (e: React.ChangeEvent<HTMLInputElement>) => void;
}

const FileUploadButton: React.FC<FileUploadButtonProps> = ({ onFileChange }) => {
    return (
        <Button variant="outlined" component="label" sx={{ marginTop: 2 }}>
            파일 업로드
            <input type="file" hidden onChange={onFileChange} />
        </Button>
    );
};

export default FileUploadButton;
