import React, {useEffect, useState} from 'react';
import axios from 'axios';
import {Route, useNavigate} from 'react-router-dom';
import {Box} from '@mui/material';
import {Board} from "../../@types/Board";
import SideBar from "../../components/SideBar/SideBar";
import {DataGrid, GridColDef} from '@mui/x-data-grid';
import useUserStore from "../../store/useUserStore";
import {PATH} from "../../constants/paths";
import useBoardStore from "../../store/useBoardStore";
import {format} from "date-fns";

const columns: GridColDef[] = [
    {field: 'cateName', headerName: '카테고리', width: 250, editable: false},
    {field: 'title', headerName: '제목', width: 250, editable: false},
    {field: 'nickName', headerName: '작성자', width: 200, editable: false},
    {
        field: 'createdAt',
        headerName: '작성일',
        width: 250,
        editable: false
    },
];

const BoardList: React.FC = () => {
    const [boardList, setBoardList] = useState<Board[]>([]);
    const [filteredBoardList, setFilteredBoardList] = useState<Board[]>([]); // 필터된 게시물 리스트
    const [selectedCategory, setSelectedCategory] = useState<string | null>(null); // 선택한 카테고리
    const navigate = useNavigate();
    const {isLoggedIn, user} = useUserStore();

    useEffect(() => {
        const fetchBoardList = async () => {
            try {
                const response = await axios.get('http://127.0.0.1:8080/api/board/list');
                setBoardList(response.data);
                setFilteredBoardList(response.data); // 전체 게시물로 초기화
            } catch (error) {
                console.error('게시판 목록을 가져오는 데 오류가 발생했습니다:', error);
            }
        };

        fetchBoardList();
    }, []);

    useEffect(() => {
        // 선택한 카테고리에 따라 게시물 필터링
        if (selectedCategory !== null) {
            setFilteredBoardList(boardList.filter(board => board.category.cateId === selectedCategory));
        } else {
            setFilteredBoardList(boardList); // 전체 게시물로 초기화
        }
    }, [selectedCategory, boardList]);
    const { setSelectedBoardId } = useBoardStore();
    const goToDetailPage = (boardId: string) => {
        console.log("Selected Board ID:", boardId); // 디버깅을 위해 로그 추가
        if (boardId) {
            setSelectedBoardId(boardId);
            navigate(`/board/detail/${boardId}`, { state: { boardId } });
        }
    };

    const handleCategorySelect = (cateId: string | null) => {
        setSelectedCategory(cateId); // 선택한 카테고리 ID 업데이트
    };
    const post = () => {
        navigate(PATH.BOARD_SAVE);
    }


    return (
        <Box sx={{display: 'flex', height: '80vh'}}>
            <Box sx={{width: '20rem'}}>
                <SideBar onCategorySelect={handleCategorySelect}/>
            </Box>

            <Box sx={{
                flexGrow: 1,
                py: 4,
                display: 'flex',
                flexDirection: 'column',
                alignItems: 'center',
                textAlign: 'center'
            }}>
                {filteredBoardList.length > 0 ? (
                    <Box sx={{height: 400, width: '90%'}}>

                        <DataGrid
                            rows={filteredBoardList.map((board) => ({
                                boardId: board.boardId,
                                createdAt: board.updatedAt ? format(new Date(board.updatedAt), 'yyyy-MM-dd HH:mm:ss') : '날짜 정보 없음', // 날짜 포맷팅 추가
                                title: board.title,
                                nickName: board.user ? board.user.nickName : '작성자 정보 없음',
                                cateName: board.category.cateName,
                            }))}
                            columns={columns}
                            pageSizeOptions={[7]}
                            onRowClick={(params) => goToDetailPage(params.row.boardId)}
                            disableRowSelectionOnClick
                            getRowId={(row) => row.boardId}
                        />
                    </Box>
                ) : (
                    <p>No boards available.</p>
                )}
                <Box>
                    {isLoggedIn && user ? (
                        <button
                            className="m-4 py-2 px-4 bg-blue-500 hover:bg-blue-700 text-white font-semibold rounded-lg shadow-md"
                            onClick={post}
                        >
                            글 작성하기
                        </button>
                    ) : (
                        <></>
                    )}
                </Box>
            </Box>

        </Box>

    );
};

export default BoardList;
