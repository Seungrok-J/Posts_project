// src/styles.ts
import { SxProps } from '@mui/material/styles';

export const commonStyles: SxProps = {
    marginTop: 2,
    marginBottom: 2,
    bgcolor: 'grey.100',
    borderColor: 'grey.300',
    borderRadius: 2,
};

export const boardContentStyles: SxProps = {
    border: 1,
    borderColor: 'grey.300',
    borderRadius: 2,
    padding: 2,
    bgcolor: 'white',
    marginTop: 4,
};

export const titleStyles: SxProps = {
    mt: 1,
    fontWeight: 'bold',
};

export const imageStyles: SxProps = {
    maxWidth: '100%',
    borderRadius: 1,
    marginTop: 2,
};

export const contentBoxStyles: SxProps = {
    mt: 4,
    minHeight: 200,
    border: 1,
    borderColor: "grey.300",
    borderRadius: 1,
    bgcolor: 'grey.100',
    padding: 2,
};
