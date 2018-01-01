package id.agristreet.agristreetapp.ui.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.agristreet.agristreetapp.R;
import id.agristreet.agristreetapp.data.local.PengelolaDataLokal;
import id.agristreet.agristreetapp.data.model.Kerjasama;
import id.agristreet.agristreetapp.presenter.KerjasamaPresenter;
import id.agristreet.agristreetapp.ui.DetailKerjasamaActivity;
import id.agristreet.agristreetapp.ui.adapter.KerjasamaAdapter;

public class KerjasamaFragment extends Fragment implements KerjasamaPresenter.View, FloatingSearchView.OnSearchListener {
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.search_view)
    FloatingSearchView searchView;

    private KerjasamaPresenter kerjasamaPresenter;
    private ProgressDialog progressDialog;
    private KerjasamaAdapter kerjasamaAdapter;

    private KerjasamaFragment.Listener listener;
    private String keyword = "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_kerjasama, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        super.onActivityCreated(savedInstanceState);
        Activity activity = getActivity();
        if (activity == null || !(activity instanceof KerjasamaFragment.Listener)) {
            throw new RuntimeException("Please implement KerjasamaFragment.Listener to use KerjasamaFragment");
        }
        listener = (KerjasamaFragment.Listener) getActivity();
        listener.onToggleSearchViewVisibility(searchView.getVisibility() == View.VISIBLE);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Mohon Tunggu...");

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        kerjasamaAdapter = new KerjasamaAdapter(getActivity());
        kerjasamaAdapter.setOnItemClickListener((view, position) ->
                onItemClick(kerjasamaAdapter.getData().get(position)));
        recyclerView.setAdapter(kerjasamaAdapter);

        searchView.setOnHomeActionClickListener(this::hideSearchView);
        searchView.setOnBindSuggestionCallback((suggestionView, leftIcon, textView, item, itemPosition) -> {
            leftIcon.setImageResource(R.drawable.ic_history_black_24dp);
        });
        searchView.setOnQueryChangeListener((oldQuery, newQuery) -> {
            keyword = newQuery.trim();
            searchView.swapSuggestions(PengelolaDataLokal.getInstance(getActivity()).getLastKeyword("kerjasama"));
        });
        searchView.setOnSearchListener(this);
        searchView.setOnFocusChangeListener(new FloatingSearchView.OnFocusChangeListener() {
            @Override
            public void onFocus() {
                searchView.setSearchBarTitle(keyword);
            }

            @Override
            public void onFocusCleared() {

            }
        });

        kerjasamaPresenter = new KerjasamaPresenter(getActivity(), this);
    }

    @Override
    public void onResume() {
        super.onResume();
        kerjasamaPresenter.loadKerjasama();
    }

    public void hideSearchView() {
        searchView.setVisibility(View.GONE);
        keyword = "";
        listener.onToggleSearchViewVisibility(false);
    }

    @OnClick(R.id.iv_search)
    public void openSearchView() {
        searchView.setSearchBarTitle(keyword);
        searchView.setVisibility(View.VISIBLE);
        searchView.setSearchFocused(true);
        listener.onToggleSearchViewVisibility(true);
    }

    private void onItemClick(Kerjasama kerjasama) {
        startActivity(DetailKerjasamaActivity.generateIntent(getActivity(), kerjasama));
    }

    @Override
    public void showError(String errorMessage) {
        Snackbar.make(recyclerView.getRootView(), errorMessage, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showLoading() {
        progressDialog.show();
    }

    @Override
    public void dismissLoading() {
        progressDialog.dismiss();
    }

    @Override
    public void showKerjasama(List<Kerjasama> daftarKerjasama) {
        kerjasamaAdapter.addOrUpdate(daftarKerjasama);
    }

    @Override
    public void onSuggestionClicked(SearchSuggestion searchSuggestion) {
        keyword = searchSuggestion.getBody().trim();
        onSearchAction(keyword);
    }

    @Override
    public void onSearchAction(String currentQuery) {
        searchView.setSearchBarTitle(keyword);
        PengelolaDataLokal.getInstance(getActivity()).addKeywordHistory("kerjasama", keyword);
        kerjasamaAdapter.filter(keyword);
    }

    public interface Listener {
        void onToggleSearchViewVisibility(boolean visible);
    }
}
