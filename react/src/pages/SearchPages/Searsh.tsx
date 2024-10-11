import React from 'react';
import axios from 'axios';
import {IconButton, TextField, Tooltip} from "@mui/material";
import SearchIcon from "@mui/icons-material/Search";

const Search = () => {

    function Search() {
        return (
            <React.Fragment>
                <Tooltip title="Search" enterDelay={1000}>
                    <div>
                        <IconButton
                            type="button"
                            aria-label="search"
                            sx={{
                                display: {xs: 'inline', md: 'none'},
                            }}
                        >
                            <SearchIcon/>
                        </IconButton>
                    </div>
                </Tooltip>
                <TextField
                    label="Search"
                    variant="outlined"
                    size="small"
                    slotProps={{
                        input: {
                            endAdornment: (
                                <IconButton type="button" aria-label="search" size="small">
                                    <SearchIcon/>
                                </IconButton>
                            ),
                            sx: {pr: 0.5},
                        },
                    }}
                    sx={{display: {xs: 'none', md: 'inline-block'}, mr: 1}}
                />

            </React.Fragment>
        );
    }
    return (
        <div>
            검색정보 표시 페이지
        </div>
    );
};

export default Search;