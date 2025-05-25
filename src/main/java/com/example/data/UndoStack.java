package com.example.data;

import com.example.model.BookingHistory;
import java.util.ArrayDeque;
import java.util.Deque;

public class UndoStack {
  private final Deque<BookingHistory> stack = new ArrayDeque<>();
  private static final UndoStack INSTANCE = new UndoStack();

  private UndoStack() {}
  
  public static UndoStack getInstance() { return INSTANCE; }
  public void push(BookingHistory bh) { stack.push(bh); }
  public BookingHistory pop()      { return stack.isEmpty() ? null : stack.pop(); }
  public boolean canUndo()         { return !stack.isEmpty(); }
}