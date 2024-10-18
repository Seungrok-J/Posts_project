export interface Comment {
    like_count: number;
    board_id: string;
    comments_id: string;
    created_at: string;
    parent_comment_id: number | null;
    updated_at: string | null;
    user_id: number;
    content: string;
}
