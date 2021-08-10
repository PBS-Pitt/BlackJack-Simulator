import java.util.Random;

public class RandIndexQueue<T> implements MyQ<T>, Indexable<T>, Shufflable {

    private T[] queue;
    private int front;
    private int logicSize;
    private int moves;
    protected int back;



    public RandIndexQueue(int sz){
        T [] temp = (T[]) new Object[sz];
        queue = temp;
        logicSize = 0;
        front = 0;
        back = 0;
    }

    public RandIndexQueue(RandIndexQueue<T> old){
        T[] temp = (T[]) new Object[old.capacity()];
        queue = temp;
        front = 0;
        back = 0;
        logicSize = 0;
        for (int i = 0; i < old.size(); i++){
            enqueue(old.get(i));
        }
        

    }

    public boolean equals(RandIndexQueue<T> rhs){
        if (logicSize != rhs.size()){
            return false;
        } else{
            int count = 0;
            for (int i = 0; i < size() ; i++){
                if (get (i) == rhs.get(i)){
                    count ++;
                }  
            }
           if (count == size()){
               return true;
           }else{
               return false;
           }
        }
    }

   
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Contents: ");
        for (int i = 0; i < size(); i++){
            sb.append(get(i)).append(" ");
        }
        return sb.toString();
    }


    @Override
    public void enqueue(T newEntry) {

        if(size() == capacity()){
            T [] temp = (T[]) new Object[queue.length * 2];
            for (int i = 0; i < logicSize; i++){
               temp [i] = queue[(front + i) % queue.length];
            }
            queue = temp;
            front = 0;
            back = logicSize;
        }
        queue[back] = newEntry;
        logicSize++;
        back = (back + 1) % queue.length;
        moves++;
    }

    @Override
    public T dequeue() {
        if (logicSize == 0){
            throw new EmptyQueueException();
        }
        T frontValue = getFront();
        queue [front] = null;
        front = (front + 1) % queue.length;
        logicSize--;
        moves++;
        return frontValue;
    }

    @Override
    public T getFront() {
        return  queue[front];

        
    }

    @Override
    public boolean isEmpty() {
        if (logicSize == 0){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public void clear() {
        for (int i = 0; i < queue.length; i++){
            queue [i] = null;
        }
        front = 0;
        back = 0;
        logicSize = 0;
    }

    @Override
    public void shuffle() {
        Random rand = new Random();
        T[] temp = (T[]) new Object[size()];
        boolean availeable;
        for (int i = 0; i < size(); i++){
            T item = get (i);
            int randPosition = rand.nextInt(size());
            availeable = false;
            while (availeable == false){
                if(temp[randPosition] == null){
                    availeable = true;
                }else{
                    randPosition = rand.nextInt(size());
                }
            } 
            temp [randPosition] = item;
        }
        queue = temp;
        front = 0;
        logicSize = size();
        back = size() % queue.length;
    }

    @Override
    public T get(int i) {
        if (size() < (i + 1)){
            throw new IndexOutOfBoundsException(); 
        }else{
            return queue[(front + i) % queue.length];
        }
    }

    @Override
    public void set(int i, T item) {
        if (size() < (i + 1)){
            throw new IndexOutOfBoundsException(); 
        }else{
            queue[(front + i)% queue.length] = item;
        }
    }

    @Override
    public int size() {
        return logicSize;
    }

    @Override
    public int capacity() {
        return queue.length;
    }

    @Override
    public int getMoves() {
        return moves;
    }

    @Override
    public void setMoves(int moves) {
        this.moves = moves;

    }

}