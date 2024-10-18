// BoardContent.tsx
import React from 'react';
import { Typography, Box } from '@mui/material';
import { boardContentStyles, titleStyles, contentBoxStyles } from "../../style"; // 공통 스타일 import

interface BoardContentProps {
    board: any;
    dirUrl: string;
}

const BoardContent: React.FC<BoardContentProps> = ({ board, dirUrl }) => {
    const formattedContent = board.content.replace(/(\r\n|\n|\r)/g, '<br />'); // 모든 줄바꿈을 <br />로 변환
    return (
        <Box sx={boardContentStyles}>
            <Typography variant="subtitle2" color="textSecondary" sx={{ borderBottom: '1px solid', borderColor: 'grey.300', pb: 1 }}>
                {board.category.cateName}
            </Typography>
            <Typography variant="h5" component="h1" sx={titleStyles}>
                {board.title}
            </Typography>
            <Box display="flex" justifyContent="space-between" mt={2}>
                <Typography variant="subtitle2" color="textSecondary">
                    작성자: {board.user ? board.user.nickName : '정보 없음'}
                </Typography>
                <Typography variant="subtitle2" color="textSecondary">
                    작성일: {new Date(board.updatedAt).toLocaleString()}
                </Typography>
            </Box>
            {board.fileName ? (
                <Box sx={{ mt: 2, borderRadius: 1, overflow: 'hidden' }}>
                    <img src={`${dirUrl}/${board.fileName}`} alt={"게시물 이미지"} style={{ maxWidth: '100%' }} />
                </Box>
            ) : (
                <Typography sx={{ mt: 2 }}>이미지가 없습니다.</Typography>
            )}
            <Box sx={contentBoxStyles}>
                <Typography variant="body1" dangerouslySetInnerHTML={{ __html: formattedContent }} />
            </Box>
        </Box>
    );
};

export default BoardContent;
