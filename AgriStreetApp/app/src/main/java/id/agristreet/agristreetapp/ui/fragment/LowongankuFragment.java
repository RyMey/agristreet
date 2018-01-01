package id.agristreet.agristreetapp.ui.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
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
import id.agristreet.agristreetapp.data.model.Lowongan;
import id.agristreet.agristreetapp.presenter.LowongankuPresenter;
import id.agristreet.agristreetapp.ui.DeskripsiLowonganActivity;
import id.agristreet.agristreetapp.ui.adapter.LowonganAdapter;

public class LowongankuFragment extends Fragment implements LowongankuPresenter.View, FloatingSearchView.OnSearchListener {
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.search_view)
    FloatingSearchView searchView;

    private LowongankuPresenter lowongankuPresenter;
    private ProgressDialog progressDialog;
    private LowonganAdapter lowonganAdapter;

    private LowongankuFragment.Listener listener;
    private String keyword = "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lowonganku, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        super.onActivityCreated(savedInstanceState);
        Activity activity = getActivity();
        if (activity == null || !(activity instanceof LowongankuFragment.Listener)) {
            throw new RuntimeException("Please implement LowongankuFragment.Listener to use LowongankuFragment");
        }
        listener = (LowongankuFragment.Listener) getActivity();
        listener.onToggleSearchViewVisibility(searchView.getVisibility() == View.VISIBLE);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Mohon Tunggu...");

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        lowonganAdapter = new LowonganAdapter(getActivity());
        lowonganAdapter.setOnItemClickListener((view, position) ->
                onItemClick(lowonganAdapter.getData().get(position)));
        recyclerView.setAdapter(lowonganAdapter);

        searchView.setOnHomeActionClickListener(this::hideSearchView);
        searchView.setOnBindSuggestionCallback((suggestionView, leftIcon, textView, item, itemPosition) -> {
            leftIcon.setImageResource(R.drawable.ic_history_black_24dp);
        });
        searchView.setOnQueryChangeListener((oldQuery, newQuery) -> {
            keyword = newQuery.trim();
            searchView.swapSuggestions(PengelolaDataLokal.getInstance(getActivity()).getLastKeyword("lowonganku"));
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

        lowongankuPresenter = new LowongankuPresenter(getActivity(), this);
    }

    @Override
    public void onResume() {
        super.onResume();
        lowongankuPresenter.loadLowonganku();
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

    private void onItemClick(Lowongan lowongan) {
        startActivity(DeskripsiLowonganActivity.generateIntent(getActivity(), lowongan));
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
    public void showLowongan(List<Lowongan> daftarLowongan) {
        lowonganAdapter.addOrUpdate(daftarLowongan);
    }

    @Override
    public void onSuggestionClicked(SearchSuggestion searchSuggestion) {
        keyword = searchSuggestion.getBody().trim();
        onSearchAction(keyword);
    }

    @Override
    public void onSearchAction(String currentQuery) {
        searchView.setSearchBarTitle(keyword);
        PengelolaDataLokal.getInstance(getActivity()).addKeywordHistory("lowonganku", keyword);
        lowonganAdapter.filter(keyword);
    }

    public interface Listener {
        void onToggleSearchViewVisibility(boolean visible);
    }
}
