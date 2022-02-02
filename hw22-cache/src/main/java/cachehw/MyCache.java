package cachehw;


import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

public class MyCache<K, V> implements HwCache<K, V> {
    //Надо реализовать эти методы
    WeakHashMap<K,V> cacheStore = new WeakHashMap<>();
    List<WeakReference<HwListener<K,V>>> weakReferenceListeners = new ArrayList<>();

    @Override
    public void put(K key, V value) {
        cacheStore.put(key,value);
        weakReferenceListeners.forEach(wrl->{
            var notWeakListener=wrl.get();
            if(notWeakListener!=null) {
               notWeakListener.notify(key, value, "put");
            }
        });
    }

    @Override
    public void remove(K key) {
         var removedValue = cacheStore.get(key) ;
        cacheStore.remove(key);
        weakReferenceListeners.forEach(wrl->{
            var notWeakListener=wrl.get();
            if(notWeakListener!=null) {
                notWeakListener.notify(key, removedValue, "remove");
            }
        });
    }

    @Override
    public V get(K key) {
        var getValue = cacheStore.get(key);
        weakReferenceListeners.forEach(wrl->{
            var notWeakListener=wrl.get();
            if(notWeakListener!=null) {
                notWeakListener.notify(key,getValue , "get");
            }
        });
        return getValue;

    }

    @Override
    public void addListener(HwListener<K, V> listener) {
        WeakReference<HwListener<K, V>> weakListener = new WeakReference<>(listener);
        weakReferenceListeners.add(weakListener);
    }

    @Override
    public void removeListener(HwListener<K, V> listener) {
        weakReferenceListeners.remove(new WeakReference<>(listener));
    }
}