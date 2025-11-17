public class BoaConstrictor extends Snake {
    private Color color;

    public BoaConstrictor(AppleEatenListener listener, Grid grid) {
        super(listener, grid);
        this.color = Color.BROWN;
    }

    @Override
    public void eat(Apple apple, Cell cell) throws DiedOfMalnutrition {
        super.eat(apple, cell);
        if (body.size() <= 1) {
            throw new DiedOfMalnutrition();
        }
        else {
            body.getLast().removeSnake();
            body.removeLast();
            onAppleEatenListener.onAppleEaten(apple, cell);
        }
    }
    
}