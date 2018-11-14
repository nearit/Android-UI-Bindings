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

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
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
    private Image image;
    @Mock
    private Bitmap bitmap;
    @Mock
    private ImageDownloadListener listener;
    @Mock
    private LoadImageFromURL task;
    @Mock
    private AsyncTask<String, Void, Image> asyncTask;

    @Captor
    private ArgumentCaptor<ImageDownloadListener> captor;
    @Captor
    private ArgumentCaptor<Image> imageCaptor;

    @Rule
    public NearLogRule emptyLoggerRule = new NearLogRule();

    private NearItImageDownloader imageDownloader;

    @Before
    public void setUp() {
        when(image.getBitmap()).thenReturn(bitmap);
        imageDownloader = new NearItImageDownloader(cacheManager, tasksManager);
    }

    @Test
    public void onDownloadImage_ifAlreadyCached_notifyListenerImmediately() {
        when(cacheManager.getBitmapFromMemCache(url)).thenReturn(bitmap);

        imageDownloader.downloadImage(url, listener);

        verify(cacheManager, never()).addBitmapToMemoryCache(anyString(), any(Bitmap.class));
        verify(listener).onSuccess(imageCaptor.capture());
        Image actualImage = imageCaptor.getValue();
        Bitmap actualBitmap = actualImage.getBitmap();
        assertThat(actualBitmap, is(bitmap));
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
    public void onDownloadImage_ifNotCachedAndNotRunning_createNewTaskAndExecIt() {
        when(cacheManager.getBitmapFromMemCache(url)).thenReturn(null);
        when(tasksManager.isAlreadyRunning(url)).thenReturn(false);
        when(tasksManager.createNewTask(any(ImageDownloadListener.class))).thenReturn(task);

        imageDownloader.downloadImage(url, listener);

        verify(tasksManager).createNewTask(any(ImageDownloadListener.class));
        verify(task).execute(url);
    }

    @Test
    public void onDownloadImage_ifSuccessful_addImageToCacheAndNotifyListener() {
        when(cacheManager.getBitmapFromMemCache(url)).thenReturn(null);
        when(tasksManager.isAlreadyRunning(url)).thenReturn(false);
        when(tasksManager.createNewTask(any(ImageDownloadListener.class))).thenReturn(task);
        when(task.execute(anyString())).thenReturn(asyncTask);

        imageDownloader.downloadImage(url, listener);

        verify(tasksManager).createNewTask(captor.capture());
        ImageDownloadListener capturedListener = captor.getValue();
        capturedListener.onSuccess(image);

        verify(cacheManager).addBitmapToMemoryCache(url, bitmap);
        verify(listener).onSuccess(image);
    }

    @Test
    public void onDownloadImage_ifError_notifyListener() {
        when(cacheManager.getBitmapFromMemCache(url)).thenReturn(null);
        when(tasksManager.isAlreadyRunning(url)).thenReturn(false);
        when(tasksManager.createNewTask(any(ImageDownloadListener.class))).thenReturn(task);
        when(task.execute(anyString())).thenReturn(asyncTask);

        imageDownloader.downloadImage(url, listener);

        verify(tasksManager).createNewTask(captor.capture());
        ImageDownloadListener capturedListener = captor.getValue();
        capturedListener.onError();

        verify(cacheManager, never()).addBitmapToMemoryCache(url, bitmap);
        verify(listener).onError();
    }

    @Test
    public void onDownloadImage_ifNotCachedAndNotRunning_addNewTask() {
        when(cacheManager.getBitmapFromMemCache(url)).thenReturn(null);
        when(tasksManager.isAlreadyRunning(url)).thenReturn(false);
        when(tasksManager.createNewTask(any(ImageDownloadListener.class))).thenReturn(task);
        when(task.execute(anyString())).thenReturn(asyncTask);

        imageDownloader.downloadImage(url, listener);

        verify(tasksManager).addTask(url, task);
    }

    @Test
    public void onDownloadImage_removeTaskAfterSuccess() {
        when(cacheManager.getBitmapFromMemCache(url)).thenReturn(null);
        when(tasksManager.isAlreadyRunning(url)).thenReturn(false);
        when(tasksManager.createNewTask(any(ImageDownloadListener.class))).thenReturn(task);
        when(task.execute(anyString())).thenReturn(asyncTask);

        imageDownloader.downloadImage(url, listener);

        verify(tasksManager).createNewTask(captor.capture());
        ImageDownloadListener capturedListener = captor.getValue();
        capturedListener.onSuccess(image);

        verify(tasksManager).removeTask(url);
    }

    @Test
    public void onDownloadImage_removeTaskAfterError() {
        when(cacheManager.getBitmapFromMemCache(url)).thenReturn(null);
        when(tasksManager.isAlreadyRunning(url)).thenReturn(false);
        when(tasksManager.createNewTask(any(ImageDownloadListener.class))).thenReturn(task);
        when(task.execute(anyString())).thenReturn(asyncTask);

        imageDownloader.downloadImage(url, listener);

        verify(tasksManager).createNewTask(captor.capture());
        ImageDownloadListener capturedListener = captor.getValue();
        capturedListener.onError();

        verify(tasksManager).removeTask(url);
    }

}