package com.kenduck10.common.collections;

import java.util.Iterator;
import java.util.List;

/**
 * ファーストクラスコレクション
 */
public abstract class FirstClassCollection<T>
    implements Iterable<T>, Iterator<T> {

  protected List<T> list;

  public List<T> list() {
    return this.list;
  }

  public int size() {
    return list.size();
  }

  public boolean isEmpty() {
    return list.isEmpty();
  }

  @Override
  public Iterator<T> iterator() {
    return this.list.iterator();
  }

  @Override
  public boolean hasNext() {
    return iterator().hasNext();
  }

  @Override
  public T next() {
    return iterator().next();
  }

  public void add(T element) {
    this.list.add(element);
  }

  public T getFirst() {
    return this.list.getFirst();
  }

  public T getLast() {
    return this.list.getLast();
  }

  public void clear() {
    this.list.clear();
  }

  public boolean contains(T element) {
    return this.list.contains(element);
  }
}
