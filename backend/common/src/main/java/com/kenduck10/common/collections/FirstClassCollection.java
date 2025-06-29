package com.kenduck10.common.collections;

import java.util.Iterator;
import java.util.List;

/**
 * ファーストクラスコレクション（First Class Collection）の抽象基底クラス。
 *
 * <p>
 * このクラスは、プリミティブなコレクション型をラップして、 ドメイン固有の振る舞いを持つコレクションクラスを作成するための基盤を提供します。
 * ファーストクラスコレクションパターンを実装することで、以下の利点を得られます：
 * </p>
 *
 * <ul>
 * <li>コレクションに対するドメイン固有の操作を定義できる</li>
 * <li>不変性を保証しやすくなる</li>
 * <li>バリデーションロジックを集約できる</li>
 * <li>型安全性が向上する</li>
 * </ul>
 *
 * <p>
 * このクラスは {@link Iterable} と {@link Iterator} の両方を実装しており、 拡張for文での反復処理や、手動でのイテレーション処理の両方をサポートします。
 * </p>
 *
 * <p>
 * <strong>使用例:</strong>
 * </p>
 * 
 * <pre>{@code
 * public class Names extends FirstClassCollection<String> {
 *   public Names(List<String> names) {
 *     this.list = new ArrayList<>(names);
 *   }
 *
 *   public boolean hasValidNames() {
 *     return list.stream().allMatch(name -> name != null && !name.trim().isEmpty());
 *   }
 * }
 * }</pre>
 *
 * @param <T> コレクションに格納される要素の型
 *
 */
public abstract class FirstClassCollection<T>
    implements Iterable<T>, Iterator<T> {

  /**
   * 内部で管理するリスト。 サブクラスでの初期化と操作を可能にするためprotectedとしています。
   */
  protected List<T> list;

  /**
   * 内部リストへの参照を返します。
   *
   * <p>
   * <strong>注意:</strong> このメソッドは内部実装への直接アクセスを提供するため、 不変性を保証したい場合は、サブクラスでこのメソッドをオーバーライドして
   * 防御的コピーを返すことを推奨します。
   * </p>
   *
   * @return 内部で管理しているリスト
   */
  public List<T> list() {
    return this.list;
  }

  /**
   * コレクションに含まれる要素数を返します。
   *
   * @return 要素数
   */
  public int size() {
    return list.size();
  }

  /**
   * コレクションが空かどうかを判定します。
   *
   * @return 空の場合は {@code true}、そうでなければ {@code false}
   */
  public boolean isEmpty() {
    return list.isEmpty();
  }

  /**
   * このコレクションの要素を反復処理するためのイテレータを返します。
   *
   * <p>
   * 拡張for文（for-each文）での使用を可能にします。
   * </p>
   *
   * @return 要素を反復処理するためのイテレータ
   */
  @Override
  public Iterator<T> iterator() {
    return this.list.iterator();
  }

  /**
   * イテレーションで次の要素が存在するかどうかを判定します。
   *
   * <p>
   * <strong>注意:</strong> この実装は新しいイテレータを毎回作成するため、 状態を保持しません。手動でのイテレーション処理には適していません。
   * 通常は拡張for文の使用を推奨します。
   * </p>
   *
   * @return 次の要素が存在する場合は {@code true}、そうでなければ {@code false}
   */
  @Override
  public boolean hasNext() {
    return iterator().hasNext();
  }

  /**
   * イテレーションの次の要素を返します。
   *
   * <p>
   * <strong>注意:</strong> この実装は新しいイテレータを毎回作成するため、 状態を保持しません。手動でのイテレーション処理には適していません。
   * 通常は拡張for文の使用を推奨します。
   * </p>
   *
   * @return 次の要素
   */
  @Override
  public T next() {
    return iterator().next();
  }

  /**
   * コレクションに要素を追加します。
   *
   * <p>
   * 不変性を保証したいコレクションでは、サブクラスでこのメソッドを オーバーライドして {@link UnsupportedOperationException} を
   * スローすることを検討してください。
   * </p>
   *
   * @param element 追加する要素
   */
  public void add(T element) {
    this.list.add(element);
  }

  /**
   * コレクションの最初の要素を返します。
   *
   * @return 最初の要素
   */
  public T getFirst() {
    return this.list.getFirst();
  }

  /**
   * コレクションの最後の要素を返します。
   *
   * @return 最後の要素
   */
  public T getLast() {
    return this.list.getLast();
  }

  /**
   * コレクションからすべての要素を削除します。
   *
   * <p>
   * 不変性を保証したいコレクションでは、サブクラスでこのメソッドを オーバーライドして {@link UnsupportedOperationException} を
   * スローすることを検討してください。
   * </p>
   */
  public void clear() {
    this.list.clear();
  }

  /**
   * 指定された要素がコレクションに含まれているかどうかを判定します。
   *
   * @param element 存在を確認する要素
   * @return 要素が含まれている場合は {@code true}、そうでなければ {@code false}
   */
  public boolean contains(T element) {
    return this.list.contains(element);
  }
}
