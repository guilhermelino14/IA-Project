package mummymaze;

public class RedMummy extends Enemy {
    public RedMummy(int line, int column) {
        super(line, column,'V', 2);
    }


    @Override
    public void particularMove(MummyMazeState state) {

        if(state.getLineHero() < line){
            if (canMoveUp(state.getMatrix())){
                move(-2, "line", state);
            }else {
                moveInColumn(state);
            }
        }else if (state.getLineHero() > line){
            if (canMoveDown(state.getMatrix())){
                move(2, "line", state);
            }else {
                moveInColumn(state);
            }
        }else {
            moveInColumn(state);
        }

    }


    private void moveInColumn(MummyMazeState state){
        char[][] matrix = state.getMatrix();

        if(state.getColumnHero() < column){
            if (canMoveLeft(matrix)){
                move(-2, "column", state);
            }
        }else if (state.getColumnHero() > column) {
            if (canMoveRight(matrix)){
                move(2, "column", state);
            }
        }
    }
}
