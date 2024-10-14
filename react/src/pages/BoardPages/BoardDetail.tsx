import React, { useEffect, useState } from 'react';
import { Board } from '../../types/Board';
import axios from 'axios';
import { useParams } from 'react-router-dom';
import AspectRatio from '@mui/joy/AspectRatio';
import Button from '@mui/joy/Button';
import Card from '@mui/joy/Card';
import CardContent from '@mui/joy/CardContent';
import CardActions from '@mui/joy/CardActions';
import Typography from '@mui/joy/Typography';
import Stack from '@mui/joy/Stack';
import { format } from 'date-fns'; // 날짜 포맷팅을 위한 라이브러리

const BoardDetail: React.FC = () => {
    const [board, setBoard] = useState<Board | null>(null);
    const { boardId } = useParams();

    // 게시물 상세 정보 API 호출
    useEffect(() => {
        const getBoard = async () => {
            try {
                console.log(`Requesting board details for boardId: ${boardId}`);
                const response = await axios.get(`http://127.0.0.1:8080/api/board/${boardId}/detail`);
                console.log('API Response:', response.data); // API 응답 확인
                setBoard(response.data);
            } catch (error) {
                console.error("Error fetching board:", error);
            }
        };

        // 호출
        getBoard();
    }, [boardId]);

    return (
        <Stack spacing={4} sx={{ alignItems: 'center', padding: 2 }}>
            <Card variant="outlined" sx={{ width: '100%', maxWidth: 600 }}>
                <CardContent>
                    <Typography level="h4" component="h2" sx={{ marginBottom: 1 }}>
                        {board?.title}
                    </Typography>
                    <Typography level="body-md" color={"neutral"}>
                        {board && format(new Date(board.createdAt), 'dd MMM yyyy')} {/* 게시물 생성일 */}
                    </Typography>
                    <AspectRatio ratio="16/9" sx={{ marginTop: 2 }}>
                        {board?.filePath && <img src={board.filePath} alt="게시물 이미지" style={{ objectFit: 'cover' }} />}
                    </AspectRatio>
                    <Typography level="body-md" sx={{ marginTop: 2 }}>
                        {board?.content} {/* 게시물 내용 */}
                    </Typography>
                </CardContent>
                <CardActions>
                    <Button variant="outlined" color="primary" size="sm" onClick={() => window.history.back()}>
                        돌아가기
                    </Button>
                </CardActions>
            </Card>
        </Stack>
    );
};

export default BoardDetail;
