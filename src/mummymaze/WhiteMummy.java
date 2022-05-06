package mummymaze;

public class WhiteMummy extends Enemy {
    public WhiteMummy(int line, int column) {
        super(line, column, StateRepresentation.WHITEMUMMY, 2);
    }

    @Override
    protected void particularMove(MummyMazeState state) {
        if(state.getColumnHero() < cellBeing.getColumn()){
            if (canMoveLeft(state.getMatrix())){
                move(-2, "column", state);
            }else {
                moveInLine(state);
            }
        }else if (state.getColumnHero() > cellBeing.getColumn()){
            if (canMoveRight(state.getMatrix())){
                move(2, "column", state);
            }else {
                moveInLine(state);
            }
        }else {
            moveInLine(state);
        }

    }

    private void moveInLine(MummyMazeState state){
        char[][] matrix = state.getMatrix();

        if(state.getLineHero() < cellBeing.getLine()){
            if (canMoveUp(matrix)){
                move(-2, "line", state);
            }
        }else if (state.getLineHero() > cellBeing.getLine()) {
            if (canMoveDown(matrix)){
                move(2, "line", state);
            }
        }
    }
}
