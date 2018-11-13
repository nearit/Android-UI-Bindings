package com.nearit.ui_bindings.utils.images;

import android.graphics.Bitmap;
import android.os.AsyncTask;

import com.nearit.ui_bindings.NearLogRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Federico Boschini
 */
@RunWith(MockitoJUnitRunner.class)
public class NearItImageDownloaderTest {

    private static final String url = "sei";

    @Mock
    private CacheManager cacheManager;
    @Mock
    private BackgroundTasksManager tasksManager;
    @Mock
    private Bitmap bitmap;
    @Mock
    private ImageDownloadListener listener;
    @Mock
    private LoadImageFromURL task;
    @Mock
    private AsyncTask<String, Void, Bitmap> asyncTask;

    @Rule
    public NearLogRule emptyLoggerRule = new NearLogRule();

    @Captor
    private ArgumentCaptor<ImageDownloadListener> captor;

    private NearItImageDownloader imageDownloader;

    @Before
    public void setUp() {
        imageDownloader = new NearItImageDownloader(cacheManager, tasksManager);
    }

    @Test
    public void onDownloadImage_ifAlreadyCached_notifyListenerImmediately() {
        when(cacheManager.getBitmapFromMemCache(url)).thenReturn(bitmap);

        imageDownloader.downloadImage(url, listener);

        verify(cacheManager, never()).addBitmapToMemoryCache(anyString(), any(Bitmap.class));
        verify(listener).onSuccess(bitmap);
    }

    @Test
    public void onDownloadImageWithTimeout_ifAlreadyCached_notifyListenerImmediately() throws InterruptedException, ExecutionException, TimeoutException {
        when(cacheManager.getBitmapFromMemCache(url)).thenReturn(bitmap);

        imageDownloader.downloadImage(url, listener, 6);

        verify(cacheManager, never()).addBitmapToMemoryCache(anyString(), any(Bitmap.class));
        verify(listener).onSuccess(bitmap);
    }

    @Test
    public void onDownloadImage_ifNotCachedAndAlreadyRunning_addListener() {
        when(cacheManager.getBitmapFromMemCache(url)).thenReturn(null);
        when(tasksManager.isAlreadyRunning(url)).thenReturn(true);
        when(tasksManager.getTask(url)).thenReturn(task);

        imageDownloader.downloadImage(url, listener);

        verify(tasksManager).getTask(url);
        verify(task).addListener(listener);
    }

    @Test
    public void onDownloadImageWithTimeout_ifNotCachedAndAlreadyRunning_addListener() throws InterruptedException, ExecutionException, TimeoutException {
        when(cacheManager.getBitmapFromMemCache(url)).thenReturn(null);
        when(tasksManager.isAlreadyRunning(url)).thenReturn(true);
        when(tasksManager.getTask(url)).thenReturn(task);

        imageDownloader.downloadImage(url, listener, 6);

        verify(tasksManager).getTask(url);
        verify(task).addListener(listener);
    }

    @Test
    public void onDownloadImage_ifNotCachedAndNotRunning_createNewTaskAndAddIt() {
        when(cacheManager.getBitmapFromMemCache(url)).thenReturn(null);
        when(tasksManager.isAlreadyRunning(url)).thenReturn(false);
        when(tasksManager.createNewTask(any(ImageDownloadListener.class))).thenReturn(task);

        imageDownloader.downloadImage(url, listener);

        verify(tasksManager).createNewTask(any(ImageDownloadListener.class));
        verify(task).execute(url);
        verify(tasksManager).addTask(url, task);
    }

    @Test
    public void onDownloadImageWithTimeout_ifNotCachedAndNotRunning_createNewTaskAndAddIt() throws InterruptedException, ExecutionException, TimeoutException {
        when(cacheManager.getBitmapFromMemCache(url)).thenReturn(null);
        when(tasksManager.isAlreadyRunning(url)).thenReturn(false);
        when(tasksManager.createNewTask(any(ImageDownloadListener.class))).thenReturn(task);
        when(task.execute(anyString())).thenReturn(asyncTask);

        imageDownloader.downloadImage(url, listener, 6);

        verify(tasksManager).createNewTask(any(ImageDownloadListener.class));
        verify(task).execute(url);
        verify(asyncTask).get(6, TimeUnit.MILLISECONDS);
        verify(tasksManager).addTask(url, task);
    }

}