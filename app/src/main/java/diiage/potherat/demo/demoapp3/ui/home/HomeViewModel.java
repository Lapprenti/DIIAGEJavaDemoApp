package diiage.potherat.demo.demoapp3.ui.home;

import android.util.Log;

import androidx.annotation.AnyRes;
import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Queue;

import javax.crypto.spec.PSource;
import javax.inject.Inject;
import diiage.potherat.demo.demoapp3.dal.repository.QuoteRepository;
import diiage.potherat.demo.demoapp3.model.Quote;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private QuoteRepository _quoteRepository;
    private MutableLiveData<Integer> quotesCount;
    private MutableLiveData<Integer> distinctAuthorCount;
    private MutableLiveData<Quote> lastQuote;


    @Inject
    @ViewModelInject
    public HomeViewModel(QuoteRepository quoteRepository) {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");

        _quoteRepository = quoteRepository;

        quotesCount = new MutableLiveData<Integer>();
        distinctAuthorCount = new MutableLiveData<Integer>();
        lastQuote = new MutableLiveData<Quote>();

        quotesCount.postValue(0);
        distinctAuthorCount.postValue(0);
    }

    public void LaunchCountQuoteProcess() {
        Thread thread = new Thread(new Runnable() {
            public void run() {
                Integer numberOfQuotes = _quoteRepository.CountAll();
                quotesCount.postValue(numberOfQuotes);
            }
        });

        thread.start();
    }

    public void LaunchCountDistinctAuthorsProcess() {
        Thread thread = new Thread(new Runnable() {
            public void run() {
                Integer numberOfDistinctAuthors = _quoteRepository.CountDistinctAuthors();
                distinctAuthorCount.postValue(numberOfDistinctAuthors);
            }
        });

        thread.start();
    }

    public void LaunchLastQuoteProcess() {
        Thread thread = new Thread(new Runnable() {
            public void run() {
                Quote lastQuoteResult = _quoteRepository.LastQuote();
                lastQuote.postValue(lastQuoteResult);
            }
        });

        thread.start();
    }

    public LiveData<String> getText() {
        return mText;
    }

    public LiveData<Integer> getQuotesCount() {
        return quotesCount;
    }

    public LiveData<Integer> getCountDistinctAuthorsProcess() { return distinctAuthorCount; }

    public LiveData<Quote> getLastQuote() { return lastQuote; }
}